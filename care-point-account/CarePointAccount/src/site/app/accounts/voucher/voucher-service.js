(function () {
    var service = function ($http, systemConfig) {
        
        this.loadAccAccounts = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-only-account");
        };
        this.currentBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch/current-branch");
        };
        this.loadBranch = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/branch");
        };
        this.loadAccBalance = function (index) {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/find-account-value/"+index);
        };
        this.loadAccTypes = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/account-type");
        };
        this.saveVoucher = function (voucher) {
            return $http.post(systemConfig.apiUrl + "/api/care-point/transaction/voucher/save", voucher);
        };
        this.delete = function (indexNo) {
            return $http.delete(systemConfig.apiUrl + "/api/care-point/account/master/acc-account/delete-account/"+ indexNo);
        };
    };
    angular.module("appModule")
            .service("voucherService", service);
}());

