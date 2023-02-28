$(document).ready(function() {
    $("#buttonAdd2Cart").on("click", function(e) {
        addToCart();
    });
});

function addToCart(){
    quantity = $("#quantity" + productId).val();

    url = contextPath + "cart/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(response) {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text(response);
        $("#myModal").modal();
    }).fail(function() {
        $("#modalTitle").text("Shopping Cart");
        $("#modalBody").text("Error while adding product to shopping cart.");
        $("#myModal").modal();
    });
}