(function () {
    var factory = function (voucherFactory, voucherService, $q, $timeout, Notification, $filter) {
        function employeeModel() {
            this.constructor();
        }
//prototype functions
        employeeModel.prototype = {
            data: {},
            tempData: {},
            currentBranch: {},
            selectAccType: {},
            userPermission: {},
            accAccountList: [],
            branchList: [],
            saveDataList: [],
            accTypeList: [],
            activeCostDepartmentList: [],
            activeCostCenterList: [],
            //constructor
            constructor: function () {
                var that = this;
                that.data = voucherFactory.Data();
                that.tempData = voucherFactory.tempData();


                voucherService.loadBranch()
                        .success(function (data) {
                            that.branchList = data;
                        });
                voucherService.currentBranch()
                        .success(function (data) {
                            that.currentBranch = data;
                            that.data.branch = data.indexNo;
                        });
                voucherService.loadAccTypes()
                        .success(function (data) {
                            that.accTypeList = data;
                        });
                voucherService.getPermission('General Voucher')
                        .success(function (data) {
                            that.userPermission = data;
                        });
                voucherService.activeCostDepartmentList()
                        .success(function (data) {
                            that.activeCostDepartmentList = data;
                        });
                voucherService.activeCostCenterList()
                        .success(function (data) {
                            that.activeCostCenterList = data;
                        });
                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                that.accAccountList = [];
                voucherService.loadAccAccounts()
                        .success(function (dataList) {
                            angular.forEach(dataList, function (data) {
                                data.accountMain = data.accMain.indexNo;
                                that.accAccountList.push(data);
                            });
                        });
            },
            getValue: function (accIndex) {
                var that = this;
                voucherService.loadAccBalance(accIndex)
                        .success(function (data) {
                            that.data.value = data;
                        });
            },

            branchLable: function (model) {
                var label;
                angular.forEach(this.branchList, function (value) {
                    if (value.indexNo === model) {
                        label = value.branchCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            accountLable: function (model) {
                var label;
                angular.forEach(this.accAccountList, function (value) {
                    if (value.indexNo === model) {
                        label = value.accCode + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            activeCostDepartmentLable: function (model) {
                var label;
                angular.forEach(this.activeCostDepartmentList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            activeCostCenterLable: function (model) {
                var label;
                angular.forEach(this.activeCostCenterList, function (value) {
                    if (value.indexNo === model) {
                        label = value.indexNo + ' - ' + value.name;
                        return;
                    }
                });
                return label;
            },
            addData: function () {
                var that = this;
                this.tempData.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                this.saveDataList.push(this.tempData);
                this.tempData = voucherFactory.tempData();
                $timeout(function () {
                    that.totalCalculation();
                }, 30);
            },
            totalCalculation: function () {
                var value = 0.00;
                angular.forEach(this.saveDataList, function (data) {
                    value += parseFloat(data.debit);
                    return;
                });
                this.data.credit = value;
            },
            save: function () {
                var that = this;
                var data = {};
                data.voucherList = this.saveDataList;
                this.data.transactionDate = $filter('date')(this.data.transactionDate, 'yyyy-MM-dd');
                this.data.chequeDate = $filter('date')(this.data.chequeDate, 'yyyy-MM-dd');
                if (this.data.payTo) {
                    this.data.costCenter = this.data.payTo;
                }else{
                    this.data.costCenter = 0;
                    
                }
                data.voucher = this.data;
                if (this.selectAccType.name === 'BANK') {
                    data.voucher.bankReconciliation = true;
                }
                var defer = $q.defer();
                console.log(data);
                voucherService.saveVoucher(JSON.stringify(data))
                        .success(function (data) {
                            defer.resolve(data);
                            that.data = voucherFactory.Data();
                            that.tempData = voucherFactory.tempData();
                        })
                        .error(function (data) {
                            Notification.error(data.message);
                            defer.reject(data);
                        });
                return defer.promise;
            },
            setClear: function () {

                this.tempData = voucherFactory.tempData();
                this.data = voucherFactory.Data();
                this.tempData.transactionDate = null;
                this.data.transactionDate = null;
                this.tempData.branch = this.currentBranch.indexNo;
                this.data.branch = this.currentBranch.indexNo;
                this.saveDataList = [];
            },
            searchVoucherByNumber: function (number) {
                var defer = $q.defer();
                var that = this;
                voucherService.findVoucherByNumberAndBranch(number)
                        .success(function (data) {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    if (!data[i].isMain) {
                                        that.tempData = data[i];
                                        that.addData();
                                    } else {
                                        that.data.accAccount = data[i].accAccount;
                                        that.data.value = data[i].value;
                                        that.data.description = data[i].description;
                                        defer.resolve();
                                    }
                                }
                            } else {
                                defer.reject();
                            }
                        });
                return defer.promise;
            }
        };
        return employeeModel;
    };
    angular.module("appModule")
            .factory("voucherModel", factory);
}());


