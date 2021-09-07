<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"

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
<form action="/epam/edit/question?id=${param.id}&testId=${question.testId}" onsubmit="return validateForm()" method="POST">
    <c:out value="${sessionScope.currentTestId}"></c:out>
    <br>
    <div class="containerErr">
    <label for="questionText">Question: </label>
        <c:if test="${empty question}">
            <input name="questionText" id="questionText" required>
        </c:if>
        <c:if test="${not empty question}">
            <input name="questionText" id="questionText" required value="${question.text}">
        </c:if>
    </div>
    <br>

    <div class="container1">
        <button class="add_form_field">Add answer &nbsp; <span style="font-size:16px; font-weight:bold;">+ </span></button>
        <br>
        <br>
        <c:if test="${empty question}">
            <div>
                <input type="text" name="name" required value="casdcs"/>
                    <select name="isCorrect">
                        <option value="false">Incorrect</option>
                        <option value="true">Correct</option>
                    </select>
                <button class="delete">Delete</button>
            </div>
            <div>
                <input type="text" name="name" required value="casdcas"/>
                    <select name="isCorrect">
                        <option value="false">Incorrect</option>
                        <option value="true">Correct</option>
                    </select>
                <button type="button" class="delete">Delete</button>
            </div>
        </c:if>

        <c:if test="${not empty question}">
            <c:forEach items="${question.answers}" var="element">
                <div>
                    <input type="text" name="name" required value="${element.text}"/>
                        <select name="isCorrect">
                            <option value="false" ${element.isCorrect == false ? 'selected' : ''}>Incorrect</option>
                            <option value="true" ${element.isCorrect == true ? 'selected' : ''}>Correct</option>
                        </select>
                    <button type="button" class="delete">Delete</button>
                </div>
            </c:forEach>
        </c:if>


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
            let curFields = document.getElementsByName("isCorrect").length;

            $(add_button).click(function(e){
                e.preventDefault();
                if (curFields < max_fields){
                    curFields++;
                    $(wrapper).append(
                        '<div><input type="text" name="name" required/>' +
                        ' <select name="isCorrect">' +
                            '<option value="false">Incorrect</option>' +
                            '<option value="true">Correct</option></select>' +
                        ' <button type="button" class="delete">Delete</button>'
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
                if (answersStatuses[i].options[answersStatuses[i].selectedIndex].value == "true") {
                    console.log("true");
                    return true;
                }
            }
            let questionText = $(".containerErr");
            $(questionText).append('<div><p><strong>There should be at least one correct answer!</strong></p></div>');
            return false;
        }

    </script>
</body>
</html>