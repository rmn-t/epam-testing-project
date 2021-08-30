<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays,java.util.List,com.epam.db.entities.Test"

%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add or Remove Input Fields Dynamically using jQuery - MyNotePaper</title>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<form action="/epam/editTest" onsubmit="return validateForm()" method="POST">
    <h1> Question :  <c:out value="${question.text}"></c:out>  </h1>

    <c:if test="${empty question.text}">
        var1 is empty or null.
    </c:if>
    <div class="container2">
    <label for="questionText">Add question: </label>
    <input name="questionText" id="questionText" required>
    </div>
    <br>
    <div class="container1">
        <button class="add_form_field">Add answer &nbsp; <span style="font-size:16px; font-weight:bold;">+ </span></button>
        <br>
        <br>
        <div><input type="text" name="name" required value="casdcs"/>
            <select name="isCorrect">
                <option value="theValueIsFalse">Incorrect</option>
                <option value="theValueIsTrue">Correct</option>
            </select>
        <a href="#" class="delete">Delete</a></div>
        <div><input type="text" name="name" required value="casdcas"/>
            <select name="isCorrect">
                <option value="theValueIsFalse">Incorrect</option>
                <option value="theValueIsTrue">Correct</option>
            </select>
        <a href="#" class="delete">Delete</a></div>
    </div>
    <br>
    <br>
    <input type="submit" value="SUBMIT">
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            let max_fields = 10;
            let wrapper = $(".container1");
            let add_button = $(".add_form_field");
            let curFields = 2;

            $(add_button).click(function(e){
                e.preventDefault();
                if (curFields < max_fields){
                    curFields++;
                    $(wrapper).append(
                        '<div><input type="text" name="name" required/>' +
                        ' <select name="isCorrect">' +
                            '<option value="theValueIsFalse">Incorrect</option>' +
                            '<option value="theValueIsTrue">Correct</option></select>' +
                        '<a href="#" class="delete">Delete</a></div>'
                    );
                } else {
                    alert('You Reached the limits')
                }
            });

            $(wrapper).on("click",".delete", function(e){
                if (curFields > 2) {
                    e.preventDefault(); $(this).parent('div').remove(); curFields--;
                }
            })
        });

        function validateForm() {
            let answersStatuses = document.getElementsByName("isCorrect");
            for (let i = 0; i < answersStatuses.length; i++) {
                if (answersStatuses[i].options[answersStatuses[i].selectedIndex].value == "theValueIsTrue") {
                    return true;
                }
            }
            let questionText = $(".container2");
            $(questionText).append('<div><p><strong>There should be at least one incorrect answer!</strong></p></div>');
            return false;
        }

    </script>
</body>
</html>