$(document).ready(function () {
    
    loadItems();
    
    var dollar = 1.00;
    var quarter = 0.25;
    var dime = 0.10;
    var nickel = 0.05;
    
    var moneyPurse = 0.00;
    
    var itemId;
    
    $('#add-dollar').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        moneyPurse = moneyPurse + dollar; 
        displayMoneyAdded(moneyPurse);
    });
    
    $('#add-quarter').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        moneyPurse = moneyPurse + quarter; 
        displayMoneyAdded(moneyPurse);
    });
    
    $('#add-dime').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        moneyPurse = moneyPurse + dime; 
        displayMoneyAdded(moneyPurse);
    });
    
    $('#add-nickel').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        moneyPurse = moneyPurse + nickel; 
        displayMoneyAdded(moneyPurse);
    });
    
    
    $('#changeReturn').click(function(event){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        
        var changeReturn = calculateChange(moneyPurse);
        displayChange(changeReturn);
        moneyPurse = 0.00;
        displayMoneyAdded(moneyPurse);
    });
        
    
    $(document).on("click", ".itemButton", function(){
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));
        if(haveValidationErrors){
            return false;
        }
        
        itemId = $(this).val();
        $('#item-number-display').replaceWith('<div id="item-number-display">' + itemId + '</div></div>');
        return itemId;
    });
    
    
    
    
    $('#make-purchase-button').click(function(event){
            var id = itemId;
            var mP = moneyPurse;
            
        
            $.ajax({
            type: 'POST',
            url: 'http://tsg-vending.herokuapp.com/money/' + mP + '/item/' + id,
            success: function(change){
                
                    var q = change.quarters;
                    var d = change.dimes;
                    var n = change.nickels;
                    var p = change.pennies;
                    
                    $('#change-given-back').replaceWith('<div class="row" id="change-given-back">' + q + ' quarter(s) ' 
                                            + d + ' dime(s) ' + n + ' nickel(s) ' + p + ' penny(s) ' + '</div>');
                    $('#message-window').val('Thank You!');
                    
                    moneyPurse = 0.00;
                    displayMoneyAdded(moneyPurse);
            },
                error: function (xhr, status, error) {
                var responseText = jQuery.parseJSON(xhr.responseText);
                $('#message-window').val( responseText.message);
                
                
            }
            })
            
        });
                      
});

function calculateChange(moneyPurse){
    moneyPurse = moneyPurse.toFixed(2);
    var userBalanceAsPennies = moneyPurse * 100;
    var quarter = 0;
    var dime = 0;
    var nickel = 0;
    var penny = 0;
    
    while (userBalanceAsPennies > 0){
        if (userBalanceAsPennies >= 25) {
                quarter++;
                userBalanceAsPennies -= 25;
            } else if (userBalanceAsPennies >= 10) {
                dime++;
                userBalanceAsPennies -= 10;
            } else if (userBalanceAsPennies >= 5) {
                nickel++;
                userBalanceAsPennies -= 5;
            } else {
                penny++;
                userBalanceAsPennies--;
            }
    }
    var changeReturn = quarter + ' quarter(s), ' + dime + ' dime(s), ' + nickel + ' nickel(s), ' + penny + ' penny(s)';
    return changeReturn;
    
}

function displayChange(changeReturn){
    $('#change-given-back').replaceWith('<div class="row" id="change-given-back">' + changeReturn + '</div>');
}

function loadItems(){
    var contentRow1 = $('#contentRow1');
    var contentRow2 = $('#contentRow2');
    var contentRow3 = $('#contentRow3');
    
    $.ajax({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function(itemArray){
            
            
            $.each(itemArray, function(index, item){
                var itemId = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
            
                var col = '<div class="col">' + 'Id: ' + itemId + ' ' + 'Name: ' + name + ' ' + '$' + price + ' ' + 'Quantity: ' + quantity + '<button type="button" class="itemButton" value="' + itemId + '">' + itemId + '</button></div>';
                contentRow1.prepend(col);
                return index < 2;
            
            });
            
            $.each(itemArray.slice(3), function(index, item){
                var itemId = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                
                var col = '<div class="col">' + 'Id: ' + itemId + ' ' + 'Name: ' + name + ' ' + '$' + price + ' ' + 'Quantity: ' + quantity + '<button type="button" class="itemButton" value="' + itemId + '">' + itemId + '</button></div>';
                contentRow2.prepend(col);
                return index < 2;
            });
            
            $.each(itemArray.slice(6), function(index, item){
                var itemId = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                
                var col = '<div class="col">' + 'Id: ' + itemId + ' ' + 'Name: ' + name + ' ' + '$' + price + ' ' + 'Quantity: ' + quantity + '<button type="button" class="itemButton" value="' + itemId + '">' + itemId + '</button></div>';
                contentRow3.prepend(col);
                return index < 2;
            });
            
            
        },
        error: function(){
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text("Error calling web service. Please try again later."));
            
        }
    });
}



function displayMoneyAdded(moneyPurse){
    moneyPurse = moneyPurse.toFixed(2);
    $('#moneyDeposited').replaceWith('<div class="row" id="moneyDeposited">' + moneyPurse + '</div>');
}

function vendItem(){
    $.ajax({
        type: 'POST',
        url: 'http://tsg-vending.herokuapp.com /money/' + $('money-in').val + '/item/' + $(id).val,
        success: function(){
            //decrement item quantity
            //calculate change
            //display change 
            //display thank you message
            
        },
        error: function(){
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text("Error calling web service. Please try again later."));
        }
    });
}

function checkAndDisplayValidationErrors(input){
    $('#errorMessages').empty();
    
    var errorMessages = [];
    
    input.each(function(){
        if(!this.validity.valid){
            var errorField = $('label[for=' + this.id +']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });
    
    if (errorMessages.length > 0){
        $.each(errorMessages,function(index,message){
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        return true;
    } else {
        return false;
    }
}