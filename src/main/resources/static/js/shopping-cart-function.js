$(document).ready(function() {
    updateTotal();
});


function updateQuantity(productId, quantity){
    url = contextPath + "cart/update/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(newSubtotal) {
        updateSubtotal();
        updateTotal();
    }).fail(function() {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error while adding product to shopping cart.");
        $("#myModal").modal();
    });




}

function updateSubtotal() {

}

function updateTotal() {
    total = 0.0;

    $(".productSubtotal").each(function(index, element) {
        total = total + parseFloat(element.innerHTML);
    });

    $("#totalAmount").text(total + "лв.");
}