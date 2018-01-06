function renderTable(items) {
    var resultTable = "<table id=\"table\">";
    resultTable += "<tr>" +
        "            <th>id</th>" +
        "            <th>description</th>" +
        "            <th>created</th>" +
        "            <th>done</th>" +
        "        </tr>";

    for (var i = 0; i < items.length; i++) {
        resultTable += "<tr>" +
            "            <td>" + items[i].id + "</td>" +
            "            <td>" + items[i].desc + "</td>" +
            "            <td>" + items[i].created + "</td>" +
            "            <td><input type=\"checkbox\" " + (items[i].done === true ? "checked" : "") + "></td>" +
            "        </tr>";
    }
    resultTable += "</table>";
    document.getElementById("items").innerHTML = resultTable;
}

function loadItems() {
    var showAll;
    if ($("#showAll").is(":checked")) {
        showAll = true;
    }
    else {
        showAll = false;
    }
    var dataString = "showAll=" + showAll;
    $.ajax('./control', {
        method: 'get',
        data: dataString,
        complete: function (data) {
            var items = JSON.parse(data.responseText);
            renderTable(items);
        }
    })
}

$(document).ready(function () {
    //checks for the button click event
    $("#showAll").change(function () {
        $(loadItems());
    });
});

$(document).ready(function () {
    //Stops the submit request
    $("#mainForm").submit(function (e) {
        e.preventDefault();
    });

    //checks for the button click event
    $("#myButton").click(function (e) {
        var description = $("input#desc").val();
        var dataString = "desc=" + description;
        var showAll;
        if ($("#showAll").is(":checked")) {
            showAll = true;
        }
        else {
            showAll = false;
        }
        dataString += "&showAll=" + showAll;
        $.ajax('./control', {
            method: 'post',
            data: dataString,
            complete: function (data) {
                var items = JSON.parse(data.responseText);
                renderTable(items);
                $('#myButton').attr("disabled", false);
            },
            beforeSend: function (jqXHR, settings) {
                //disable the button until we get the response
                $('#myButton').attr("disabled", true);
            }
        });
    });
});

function init() {
    loadItems();
}

window.onload = init;