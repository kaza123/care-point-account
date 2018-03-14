(function () {
    var factory = function (voucherFactory, voucherService, $q, $timeout, $filter) {
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
                            console.log(data);
                            that.userPermission = data;
                        });
                this.loadAccAccount();
            },
            loadAccAccount: function () {
                var that = this;
                voucherService.loadAccAccounts()
                        .success(function (data) {
                            that.accAccountList = data;
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
                    console.log(data);
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
                data.voucher = this.data;
                if (this.selectAccType.name === 'BANK') {
                    data.voucher.bankReconciliation = true;
                }

                console.log(data);

                var defer = $q.defer();
                voucherService.saveVoucher(JSON.stringify(data))
                        .success(function (data) {
                            defer.resolve(data);
                        })
                        .error(function (data) {
                            defer.reject(data);
                        });
                return defer.promise;
            },
            setClear: function () {

                this.tempData = voucherFactory.tempData();
                this.data = voucherFactory.Data();
                this.tempData.transactionDate = new Date();
                this.data.transactionDate = new Date();
                this.tempData.branch = this.currentBranch.indexNo;
                this.data.branch = this.currentBranch.indexNo;
                this.saveDataList = [];
            },
            searchVoucherByNumber: function (number) {
                var defer = $q.defer();
                var that = this;
                voucherService.findVoucherByNumberAndBranch(number)
                        .success(function (data) {
                            console.log(data);
                            if (data.length > 0) {
//                                for (var i = 0; i < data.length; i++) {
//                                    that.tempData = data[i];
//                                    that.addData();
//                                }
//                                that.data.accAccount = data[1].accAccount;
//                                that.data.value = data[1].value;
//                                that.data.description = data[1].description;
//                                defer.resolve();
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

