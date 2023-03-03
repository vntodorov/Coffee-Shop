$(document).ready(function() {
    updateTotal();
});

function updateTotal() {
    total = 0.0;

    $(".productSubtotal").each(function(index, element) {
        total = total + parseFloat(element.innerHTML);
    });

    $("#totalAmount").text(total + "лв.");
}