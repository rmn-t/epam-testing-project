<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:bundle basename="messages">

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
    </style>
    <title><fmt:message key="editQuestion" /></title>
</head>
    <body>

        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

         <div style="margin-top: 2%;"class="container">
            <div class="row">
            <div class="col"></div>
            <div class="col-8">
            <div class="card">
            <article class="card-body">
                <form action="/epam/edit/question?id=${param.id}&testId=${param.testId}" onsubmit="return validateForm()" method="POST">
                    <br>
                    <div class="containerErr">
                        <div class="mb-2 text-center">
                            <label for="questionText" class="form-label"><fmt:message key="questionText" /></label>
                            <c:if test="${empty question}">
                                <input type="text" class="form-control" name="questionText" id="questionText" placeholder="<fmt:message key='questionText' />" value="" required
                                oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                oninput="this.setCustomValidity('')"
                                maxlength="400"
                                >
                            </c:if>
                            <c:if test="${not empty question}">
                                <input type="text" class="form-control" name="questionText" id="questionText" placeholder="<fmt:message key='questionText' />" value="${question.text}" required
                                oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                oninput="this.setCustomValidity('')"
                                maxlength="400"
                                >
                            </c:if>
                        </div>
                    </div>
                    <div class="answers">
                        <div class="row text-center">
                            <div class="col"></div>
                            <div class="col"><button class="add_form_field btn btn-primary"><fmt:message key='addAnswer' /></button></div>
                            <div class="col"></div>
                        </div>

                        <br>
                        <c:if test="${empty question}">
                            <div class="row mb-2 text-center q-row">
                                <div class="col">
                                    <input type="text" class="form-control" name="name" id="name" placeholder="<fmt:message key='questionAnswer' />" value="" required maxlength="400"
                                    oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                    oninput="this.setCustomValidity('')"
                                    >
                                </div>
                                <div class="col-3">
                                    <select class="form-select text-center" name="isCorrect" id="isCorrect">
                                        <option value="false"><fmt:message key='incorrect' /></option>
                                        <option value="true"><fmt:message key='correct' /></option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <button type="button" type="button" class="delete-question-btn btn btn-danger"><fmt:message key='delete' /></button>
                                </div>
                            </div>
                            <div class="row mb-2 text-center q-row">
                                <div class="col">
                                    <input type="text" class="form-control" name="name" id="name" placeholder="<fmt:message key='questionAnswer' />" value="" required maxlength="400"
                                    oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                    oninput="this.setCustomValidity('')"
                                    >
                                </div>
                                <div class="col-3">
                                    <select class="form-select text-center" name="isCorrect" id="isCorrect">
                                        <option value="false"><fmt:message key='incorrect' /></option>
                                        <option value="true"><fmt:message key='correct' /></option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <button type="button" type="button" class="delete-question-btn btn btn-danger"><fmt:message key='delete' /></button>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty question}">
                            <c:forEach items="${question.answers}" var="element">
                                <div class="row mb-2 text-center q-row">
                                    <div class="col">
                                        <input type="text" class="form-control" name="name" id="name" placeholder="<fmt:message key='questionAnswer' />" value="${element.text}" required maxlength="400"
                                        oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                        oninput="this.setCustomValidity('')"
                                        >
                                    </div>
                                    <div class="col-3">
                                        <select class="form-select text-center" name="isCorrect" id="isCorrect">
                                            <option value="false" ${element.isCorrect == false ? 'selected' : ''}><fmt:message key='incorrect' /></option>
                                            <option value="true" ${element.isCorrect == true ? 'selected' : ''}><fmt:message key='correct' /></option>
                                        </select>
                                    </div>
                                <div class="col-2">
                                    <button type="button" type="button" class="delete-question-btn btn btn-danger"><fmt:message key='delete' /></button>
                                    </div>
                                </div>

                            </c:forEach>
                        </c:if>
                    </div>

                    <br>
                <div class="row mb-2 text-center">
                    <div class="col"></div>
                    <div class="col"><input class="btn btn-success" type="submit" value="<fmt:message key='saveChanges' />"></div>
                    <div class="col"></div>
                </div>
                </form>
                </article>
            </div>
            </div>
            <div class="col"></div>
        </div>

        <script>
            $(document).ready(function() {
                let max_fields = 10;
                let wrapper = $(".answers");
                let add_button = $(".add_form_field");
                let curFields = document.getElementsByName("isCorrect").length;

                $(add_button).click(function(e){
                    e.preventDefault();
                    if (curFields < max_fields){
                        curFields++;
                        $(wrapper).append(
                            '<div class="row mb-2 text-center q-row">' +
                                '<div class="col">' +
                                    '<input type="text" class="form-control" name="name" id="name" placeholder="' + "<fmt:message key='questionAnswer' />" +  '" value="" required maxlength="400"'+
                                     'oninvalid="this.setCustomValidity("' + '<fmt:message key="msg.fillOutThisField" />' + '") ' +
                                     'oninput="this.setCustomValidity(' + '""' + ')" >' +
                                '</div>' +
                                '<div class="col-3">' +
                                    '<select class="form-select text-center" name="isCorrect" id="isCorrect">' +
                                        '<option value="false"><fmt:message key="incorrect" /></option>' +
                                        '<option value="true"><fmt:message key="correct" /></option>' +
                                    '</select>' +
                                '</div>' +
                                '<div class="col-2">' +
                                    '<button type="button" class="delete-question-btn btn btn-danger"><fmt:message key="delete" /></button>' +
                                '</div>' +
                            '</div>'
                        );
                    } else {
                        alert('You Reached the limits')
                    }
                });

                $(wrapper).on("click",".delete-question-btn", function(e){
                    if (curFields > 2) {
                        e.preventDefault(); $(this).closest('.q-row').remove(); curFields--;
                        // e.preventDefault(); $(this).parent('div').remove(); curFields--;
                    } else if (document.getElementById("alert-num-answers") == null) {
                        let questionText = $(".containerErr");
                        $(questionText).append(
                            '<div class="alert alert-info alert-dismissible fade show text-center" id="alert-num-answers" role="alert">' +
                                '<strong><fmt:message key="msg.atLeastTwoAnswer" /></strong>' +
                                '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                            '</div>'
                        );
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
                if (document.getElementById("alert-correct-answers") == null) {
                    $(questionText).append(
                        '<div class="alert alert-danger alert-dismissible fade show text-center" id="alert-correct-answers" role="alert">' +
                            '<strong><fmt:message key="msg.atLeastOneCorrectAnswer" /></strong>' +
                            '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                        '</div>'
                    );
                }
                return false;
            }

        </script>

        </fmt:bundle>
    </body>
</html>