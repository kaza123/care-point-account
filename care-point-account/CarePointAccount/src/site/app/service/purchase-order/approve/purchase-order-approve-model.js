(function () {
    angular.module("appModule")
            .factory("purchaseOrderApproveModel", function (PurchaseOrderRequestService, Notification, PurchaseOrderRequestModelFactory, $q) {
                function purchaseOrderApproveModel() {
                    this.constructor();
                }
                purchaseOrderApproveModel.prototype = {

                    data: {},
                    tempData: {},
                    summaryData: {},
                    //master data lists
                    pendingPurchaseOrderList: [],
                    suppliers: [],
                    allItems: [],
                    grandTotalValue: 0.00,

                    constructor: function () {
                        var that = this;
                        this.data = PurchaseOrderRequestModelFactory.newData();
                        this.tempData = PurchaseOrderRequestModelFactory.tempData();
                        this.summaryData = PurchaseOrderRequestModelFactory.summaryData();

                        this.loadPendingPurchaseOrder();

                        PurchaseOrderRequestService.loadSuppliers()
                                .success(function (data) {
                                    that.suppliers = data;
                                });
                        PurchaseOrderRequestService.loadItems()
                                .success(function (data) {
                                    that.allItems = data;
                                });
                    },
                    getSupplier: function (supplier) {
                        var label;
                        angular.forEach(this.suppliers, function (value) {
                            if (value.indexNo === supplier) {
                                label = value.indexNo + ' - ' + value.name;
                                return;
                            }
                        });
                        return label;
                    },
                    getItem: function (item) {
                        var itemObject;
                        angular.forEach(this.allItems, function (value) {
                            if (value.indexNo === item) {
                                itemObject = value;
                                return;
                            }
                        });
                        return itemObject;
                    },
                    itemLable: function (index) {
                        var label;
                        angular.forEach(this.allItems, function (value) {
                            if (value.indexNo === index) {
                                label = value.barcode + ' - ' + value.name;
                                return;
                            }
                        });
                        return label;
                    },
                    calculatePendingGrandTotal: function () {
                        var value = 0.00;
                        angular.forEach(this.pendingPurchaseOrderList, function (value) {
                            value += parseFloat(value.grandTotal2);
                        });
                        return value;
                    },
                    loadPendingPurchaseOrder: function () {
                        var that = this;
                        that.grandTotalValue = 0.00;

                        PurchaseOrderRequestService.loadPendingPurchaseOrder()
                                .success(function (data) {
                                    angular.forEach(data, function (value) {
                                        that.grandTotalValue += parseFloat(value.grandTotal);
                                        value.grandTotal2 = value.grandTotal;
                                    });
                                    that.pendingPurchaseOrderList = data;
                                });
                    },
                    selectPurchaseOrder: function (purchaseOrder) {
                        var data = purchaseOrder;
                        this.data = data;
                        this.summaryCalculator();
                    },
                    summaryCalculator: function () {
                        var that = this;
                        var qty = 0;
                        var val = 0;
                        var discount = 0;
                        var itemValue = 0;
                        angular.forEach(that.data.purchaseOrderItemList, function (value) {
                            qty = parseFloat(qty) + parseFloat(value.qty);
                            val = parseFloat(val) + parseFloat(value.value);
                            discount = parseFloat(discount) + parseFloat(value.discountValue);
                            itemValue = parseFloat(itemValue) + parseFloat(value.netValue);
                        });
                        this.summaryData.qty = qty;
                        this.summaryData.value = val;
                        this.summaryData.discountValue = discount;
                        this.data.itemValue = (itemValue).toFixed(2);
                        if (this.data.nbt) {
                            this.data.nbtValue = ((this.data.itemValue * this.data.nbt) / 100).toFixed(2);
                        }
                        if (this.data.vat) {
                            this.data.vatValue = (((this.data.itemValue + this.data.nbtValue) * this.data.vat) / 100).toFixed(2);
                        }
                        that.data.grandTotal = (itemValue + this.data.nbtValue + this.data.vatValue).toFixed(2);
                    },
                    save: function () {
                        var defer = $q.defer();
                        var that = this;

                        var saveConfirmation = true;

                        if (!that.data.supplier) {
                            saveConfirmation = false;
                            Notification.error("Select Supplier for Approve Purchase Order !");
                        }
                        if (!that.data.date) {
                            saveConfirmation = false;
                            Notification.error("Select Date for Save Approve Order !");
                        }
                        if (!that.data.approvedDate) {
                            saveConfirmation = false;
                            Notification.error("Select Date for Approve Purchase Order !");
                        }
                        if (!that.data.deliverDate) {
                            saveConfirmation = false;
                            Notification.error("Select DeliverDate for Approve Purchase Order !");
                        }
                        if (that.data.purchaseOrderItemList.length === 0) {
                            saveConfirmation = false;
                            Notification.error("Add Purchase Order Item for Save Purchase Order !");
                        }

                        if (saveConfirmation) {
                            var that = this;
                            this.data.grandAmount = this.data.grandTotal;
                            PurchaseOrderRequestService.savePurchaseOrderApprove(JSON.stringify(this.data))
                                    .success(function (data) {
                                        that.clear();
                                        defer.resolve(data);
                                    })
                                    .error(function (data) {
                                        defer.reject(data);
                                    });
                            return defer.promise;
                        }
                    },
                    delete: function (indexNo) {
                        this.data.purchaseOrderItemList.splice(indexNo, 1);
                        this.summaryCalculator();
                    },
                    edit: function (indexNo) {
                        this.tempData = this.data.purchaseOrderItemList[indexNo];
                        this.data.purchaseOrderItemList.splice(indexNo, 1);
                        this.tempData.barcode = this.getItem(this.tempData.item).barcode;
                        this.summaryCalculator();
                    },
                    discard: function () {
                        var that = this;
                        PurchaseOrderRequestService.deletePurchaseOrder(this.data.indexNo)
                                .success(function (data) {
                                    Notification.success('Selected Pending Purchase Order Deleted Success !');
                                    that.clear();
                                });

                    },
                    addData: function () {
                        var that = this;
                        var saveConfirmation = true;

                        if (!that.tempData.item) {
                            saveConfirmation = false;
                            Notification.error("select item !");
                        }
                        if (!that.tempData.price) {
                            saveConfirmation = false;
                            Notification.error("add price !");
                        }
                        if (!that.tempData.qty) {
                            saveConfirmation = false;
                            Notification.error("add quantity !");
                        }
                        if (saveConfirmation) {
                            this.data.purchaseOrderItemList.unshift(this.tempData);
                            this.tempData = {};
                            this.summaryCalculator();
                        }
                    },
                    clear: function () {
                        this.data = PurchaseOrderRequestModelFactory.newData();
                        this.tempData = PurchaseOrderRequestModelFactory.tempData();
                        this.summaryData = PurchaseOrderRequestModelFactory.summaryData();
                        this.loadPendingPurchaseOrder();
                    }
                };
                return purchaseOrderApproveModel;
            });
}());
