(function(angular){
var app = angular.module('spendManager', ['ngMaterial']);
	app.controller('spendCtrl', function($scope, $http, $mdDialog,$filter) {

		$scope.AllTypes = ['food','shopping','entertainment','misc'];
		$scope.maxBudget = 9000;
		$scope.fetchAllSpends = function(){
			console.log("fetching spends");
			$http({
			    url: "http://localhost:7990/v1/fetch_expenses/111", 
			    method: "GET"
			 }).then(function(data){
				 console.log(data);
				 $scope.userExpenses = data.data;
				 $scope.totalAmountSpent = data.data.reduce(function(a, b) { return a + b['expenseAmount']; }, 0).toFixed(2);
				 $scope.showChart($scope.userExpenses);
			 }).catch(function(err){
				 console.log(err);
			 });
		}
		
		$scope.fetchPDF = function(){
			var doc = new jsPDF();
		    doc.addHTML(document.getElementById("gridDiv"), function() {
		       doc.save("Expense.pdf");
		    });
		}
		
		$scope.showChart = function(userExpenses){
			console.log(userExpenses);
			var expenseMap = {};
			for(var i = 0 ; i < userExpenses.length ; i++){
				if(userExpenses[i].expenseType == "shopping"){
					if(expenseMap['shopping'] != null )
						expenseMap['shopping'] += userExpenses[i].expenseAmount;
					else
						expenseMap['shopping'] = userExpenses[i].expenseAmount;
				}
				else if(userExpenses[i].expenseType == "misc"){
					if(expenseMap['misc'] != null )
						expenseMap['misc'] += userExpenses[i].expenseAmount;
					else
						expenseMap['misc'] = userExpenses[i].expenseAmount;
				}
				else if(userExpenses[i].expenseType == "food"){
					if(expenseMap['food'] != null )
						expenseMap['food'] += userExpenses[i].expenseAmount;
					else
						expenseMap['food'] = userExpenses[i].expenseAmount;
				}
				else if(userExpenses[i].expenseType == "entertainment"){
					if(expenseMap['entertainment'] != null )
						expenseMap['entertainment'] += userExpenses[i].expenseAmount;
					else
						expenseMap['entertainment'] = userExpenses[i].expenseAmount;
				}
			}
			console.log(expenseMap);
			var ctx = document.getElementById('expenseChart').getContext('2d');
			var chart = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: ['Food', 'Shopping', 'Entertainment', 'Misc'],
					datasets: [{
						label: 'User Spend Chart',
						backgroundColor: 'rgb(76, 192, 255)',
						borderColor: 'rgb(76, 192, 255)',
						data: [expenseMap['food'],expenseMap['shopping'],expenseMap['entertainment'],expenseMap['misc']]
					}]
				},
				options: {}
			});
		}
		
		$scope.filterSpends = function(){
			if ($scope.startDate != undefined & $scope.endDate != undefined) {
                if ( new Date($scope.startDate) > new Date($scope.endDate)) {
                	$scope.endDate = undefined;
                    alert("End date should be greater than or equal to start date");
                    return;
                }
            }
			var startDate = $filter('date')($scope.startDate, "yyyy-MM-dd");
			var endDate = $filter('date')($scope.endDate, "yyyy-MM-dd");
			var expendType = $scope.expendType1;
			$http({
			    url: "http://localhost:7990/v1/fetch_expenses_for_chart/111/"+startDate+"/"+endDate+"/"+expendType, 
			    method: "GET"
			 }).then(function(data){
				 console.log(data);
				 $scope.showChart(data.data);
			 }).catch(function(err){
				 console.log(err);
			 });
		}
		
		$scope.showAlert = function(maxBdgt,totAmtSpt){
		    $mdDialog.show({
		      controller: function($scope, $mdDialog){
		    	  $scope.types = ['food','shopping','entertainment','misc'];
		    	  $scope.close = function($event) {
		                $mdDialog.cancel();
		          };
		          $scope.refreshView = function(){
		        	  $http({
		  			    url: "http://localhost:7990/v1/fetch_expenses/111", 
		  			    method: "GET"
		  			 }).then(function(data){
		  				 console.log(data);
		  				 $scope.userExpenses = data.data;
		  			 }).catch(function(err){
		  				 console.log(err);
		  			 });
		          }
		          
		          $scope.fetchTrends = function(typeToSpent,spentDate,amountToSpent){
		  			$http({
					    url: "http://localhost:7990/v1/fetch_trends/111/"+typeToSpent+"/"+amountToSpent+"/"+totAmtSpt, 
					    method: "GET"
					 }).then(function(data){
						 console.log(data.data);
						 $scope.showConfirm(typeToSpent,spentDate,amountToSpent,data.data);
					 }).catch(function(err){
						 console.log(err);
					 });
		  		  }
		          
		          $scope.showConfirm = function(etype,dateNew,eamt,data){
		              var confirm = $mdDialog.confirm()
		                   .textContent(data.message)
		                   .ariaLabel('confirm')
		                   .ok('Yes')
		                   .cancel('No');

		               $mdDialog.show(confirm).then(function() {
		            	     $http({
				  			    url: "http://localhost:7990/v1/add_user_expenses/111", 
				  			    method: "POST",
				  			    data:{"expenseAmount":eamt,"expenseType":etype,"expenseDate":dateNew} ,
				  			    headers: {'Content-Type': 'application/json'}
				  			 }).then(function(data){
				  				 console.log(data);$scope.close();
				  				 $scope.refreshView();
				  			 }).catch(function(err){
				  				 console.log(err);
				  			 });
		               }, function() {
		                console.log('not confirmed')
		               }); 
		          }
		          
		          $scope.addSpend = function(etype,edate,eamt) {
		  			var dateNew = $filter('date')($scope.edate, "yyyy-MM-dd");
		  			if(parseFloat(totAmtSpt) + eamt > parseFloat(maxBdgt)){
		  				$scope.fetchTrends(etype,dateNew,eamt);
		  			} else {
		  				$http({
		  			    url: "http://localhost:7990/v1/add_user_expenses/111", 
		  			    method: "POST",
		  			    data:{"expenseAmount":eamt,"expenseType":etype,"expenseDate":dateNew} ,
		  			    headers: {'Content-Type': 'application/json'}
		  			 }).then(function(data){
		  				 console.log(data);$scope.close();
		  				 $scope.refreshView();
		  			 }).catch(function(err){
		  				 console.log(err);
		  			 });
		  			}
		  		  }
		      },
		      template: '<md-dialog aria-label="My Dialog" style="width: 400px;height:300px">'+
		                    '<md-dialog-content>' 
		                    + 'Type :: <select ng-model="etype" ng-options="x for x in types"></select><br/><br/> Date :: <md-datepicker ng-model="edate"></md-datepicker><br/><br/> Amount :: <input ng-model="eamt" type="number" min="1"></input>'+
		                    '</md-dialog-content>' +
		                    '<md-button ng-click=close()>Close</md-button>' +
		                    '<md-button ng-click=addSpend(etype,edate,eamt) ng-disabled="etype == undefined || edate == undefined || eamt == undefine">Add</md-button>' + 
		                    '</md-dialog>',
		      targetEvent: event
		    });
		  };
	});
	
})(window.angular);