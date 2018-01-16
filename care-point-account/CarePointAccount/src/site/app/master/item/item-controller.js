(function () {
    angular.module("itemModule", ['ui.bootstrap', 'ui-notification']);
    angular.module("itemModule")
            .controller("itemController", function ($scope, itemModel, $timeout, Notification, ConfirmPane) {
                $scope.model = new itemModel();
                $scope.ui = {};

                $scope.ui.toggleType = function (functions) {
                    if (functions === "ITEMS") {
                        $scope.ui.saveMode = "ITEMS";

                    } else if (functions === "ITEMS_UNITS") {
                        $scope.ui.saveMode = "ITEMS_UNITS";

                    } else if (functions === "PACKAGE_ITEMS") {
                        $scope.ui.saveMode = "PACKAGE_ITEMS";

                    } else if (functions === "CONSUMABLE_ITEMS") {
                        $scope.ui.saveMode = "CONSUMABLE_ITEMS";

                    } else if (functions === "ITEM_CHECK_DETAIL") {
                        $scope.ui.saveMode = "ITEM_CHECK_DETAIL";

                    } else if (functions === "PRICE_CATEGORY_DETAIL") {
                        $scope.ui.saveMode = "PRICE_CATEGORY_DETAIL";
                    }
                    $scope.focus('#name');

                };

                //--------------------- item ---------------------
                $scope.ui.new = function () {
                    $scope.ui.mode = "EDIT";
                    $scope.focus('#name');
                };
                $scope.focus = function (id) {
                    $timeout(function () {
                        document.querySelectorAll(id)[0].focus();
                    }, 10);

                };

                $scope.ui.clear = function () {
                    Notification.success("all details clear");
                    $scope.model.clear();
                };

                $scope.ui.selectStock = function (type) {
                    if (type === 'STOCK') {
                        $scope.textViewMode = 'STOCK';
                    }
                    if (type === 'NON STOCK') {
                        $scope.textViewMode = 'NON STOCK';
                    }
                    if (type === 'SERVICE') {
                        $scope.textViewMode = 'SERVICE';
                    }
                    if (type === 'PACKAGE') {
                        $scope.textViewMode = 'PACKAGE';
                    }
                };

                //find item by item type
                $scope.ui.findItemByItemType = function (searchItemType) {
                    $scope.model.findItemByItemType(searchItemType);
                };

                //save item
                $scope.ui.saveItems = function () {
                    var check = true;
                    if (!$scope.model.itemData.type) {
                        check = false;
                        Notification.error("enter item type");
                    }
                    if (!$scope.model.itemData.name) {
                        check = false;
                        Notification.error("enter item name");
                    }
                    if (!$scope.model.userPermission.add) {
                        ch = false;
                        Notification.error('you have no permission to save new item !');
                    }
                    if (check) {
                        ConfirmPane.primaryConfirm("do you want to save Item !")
                                .confirm(function () {
                                    $scope.model.saveItem()
                                            .then(function () {
                                                Notification.success("Item save Success");
                                            }, function () {
                                                Notification.error("Item save Fail");
                                            });

                                })
                                .discard(function () {
                                    console.log('discard');
                                });
                    }
                };

                //save consumable item
                $scope.ui.saveConsumable = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        check = false;
                        Notification.error('you have no permission to save new Consumable Item !');
                    }
                    if (check) {
                        ConfirmPane.primaryConfirm("Save this Consumable Item !")
                                .confirm(function () {
                                    $scope.model.saveConsumable()
                                            .then(function () {
                                                Notification.success("Consumable Item Save Success");
                                            }, function () {
                                                Notification.error("Consumable Item Save Fail");
                                            });

                                })
                                .discard(function () {
                                    console.log('discard');
                                });
                    }
                };



                //edit item
                $scope.ui.editeItems = function (items, $index) {
                    var check = true;
                    if (!$scope.model.userPermission.update) {
                        ch = false;
                        Notification.error('you have no permission to edit Item !');
                    }
                    if (check) {
                        $scope.textViewMode = items.type;
                        $scope.model.editeItem(items, $index);
                    }
                };

                //delete item
                $scope.ui.deleteItems = function (items, $index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        ch = false;
                        Notification.error('you have no permission to delete Item !');
                    }
                    if (check) {
                        $scope.model.deleteItem(items, $index);
                    }
                };



                //---------------------item unit ---------------------
                //save item units
                $scope.ui.saveItemUnits = function () {
                    if (!$scope.model.itemUnitData.item) {
                        Notification.error("select item");
                    } else if (!$scope.model.itemUnitData.name) {
                        Notification.error("enter item unit name");
                    } else if (!$scope.model.itemUnitData.qty) {
                        Notification.error("enter item unit qty");
                    } else if (!$scope.model.itemUnitData.salePriceNormal) {
                        Notification.error("enter item unit sale price normal");
                    } else if (!$scope.model.itemUnitData.salePriceRegister) {
                        Notification.error("enter item cost sale price register");
                    } else if ($scope.model.itemUnitData.item
                            && $scope.model.itemUnitData.name
                            && $scope.model.itemUnitData.qty
                            && $scope.model.itemUnitData.salePriceNormal
                            && $scope.model.itemUnitData.salePriceRegister) {
                        var check = true;
                        if (!$scope.model.userPermission.add) {
                            ch = false;
                            Notification.error('you have no permission to save new Item unit !');
                        }
                        if (check) {
                            $scope.model.saveItemUnit();
                            $scope.itemType = null;
                            $scope.itemUnit = null;
                        }
                    }

                };
                $scope.ui.showDetails = function () {
                    var show = true;
                    if (!$scope.itemType) {
                        show = false;
                    }
                    if (!$scope.itemUnit) {
                        show = false;
                    }
                    if (!$scope.model.itemUnitData.item) {
                        show = false;
                    }
                    return show;
                };

                //edit item units
                $scope.ui.editeItemUnits = function (itemsUnits, $index) {
                    var check = true;
                    if (!$scope.model.userPermission.update) {
                        ch = false;
                        Notification.error('you have no permission to edit Item unit !');
                    }
                    if (check) {
                        $scope.model.editeItemUnits(itemsUnits, $index);
                    }
                };

                $scope.ui.getItemType = function (model) {
                    $scope.itemType = $scope.model.item(model).type;
                    $scope.itemUnit = $scope.model.item(model).unit;
                    $scope.itemObject = $scope.model.item(model);
                    //item selected get item wise item units
                    $scope.model.loadItemUnitByItem(model);
                };

                //delete item units
                $scope.ui.deleteItemUnits = function (itemsUnits, $index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        ch = false;
                        Notification.error('you have no permission to delete item unit !');
                    }
                    if (check) {
                        $scope.model.deleteItemUnits(itemsUnits, $index);
                    }
                };

                //--------------------- package ---------------------
                //save package items
                $scope.ui.addPackageItem = function () {
                    var check = true;
                    if (!$scope.model.userPermission.add) {
                        ch = false;
                        Notification.error('you have no permission to save new package Item !');
                    }
                    if (check) {
                        if (!$scope.model.packageData.packages) {
                            Notification.error("select package item");
                        } else if (!$scope.model.packageData.item) {
                            Notification.error("select item");
                        } else if ($scope.model.packageData.packages && $scope.model.packageData.item) {
                            $scope.model.addPackageItem();
                        }
                    }
                };

                //select package - get package item list
                $scope.ui.getPackageItems = function (model) {
                    $scope.model.getPackageItems(model);
                };

                //consumable Item
                $scope.ui.deleteConsumableItem = function (index) {
                    var check = true;
                    if (!$scope.model.userPermission.delete) {
                        check = false;
                        Notification.error('you have no permission to delete Consumable Item !');
                    }
                    if (check) {
                        ConfirmPane.dangerConfirm("Delete Selected Consumable Item !")
                                .confirm(function () {
                                    $scope.model.deleteConsumableItem(index)
                                            .then(function () {
                                                Notification.success("Consumable Item Delete Success");
                                            }, function () {
                                                Notification.error("Consumable Item Delete Fail");
                                            });

                                })
                                .discard(function () {
                                    console.log('discard fail');
                                });
                    }
                };
               
                $scope.ui.selectPackageItem = function (model) {
                    $scope.model.selectPackageItem(model);
                };

                //init
                $scope.ui.init = function () {
                    $scope.ui.mode = "IDEAL";
                    $scope.ui.type = "NORMAL";
                };
                $scope.ui.init();
            });
}());