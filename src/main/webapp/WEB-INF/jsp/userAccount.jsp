<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constants.PagePathConstants" %>
<%@ page import="com.softserve.edu.constants.FormParameterConstants" %>
<%@ page import="com.softserve.edu.constants.AttributeValueConstants" %>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="form-block center-block">
            <form action="${pageTitle == AttributeValueConstants.ACCOUNT_PAGE ? PagePathConstants.USER_ACCOUNT : PagePathConstants.SIGNUP}"
                  class="col" method="post">
                <c:if test="${not empty error}">
                    <div id="error-block" class="center">
                        <span>${error.message}</span>
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.FIRST_NAME}" id="${FormParameterConstants.FIRST_NAME}" type="text"
                           class="validate" value="${user.firstName}" required>
                    <label for="${FormParameterConstants.FIRST_NAME}">First Name</label>
                </div>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.LAST_NAME}" id="${FormParameterConstants.LAST_NAME}" type="text"
                           class="validate" value="${user.lastName}" required>
                    <label for="${FormParameterConstants.LAST_NAME}">Last Name</label>
                </div>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.LOGIN}" id="${FormParameterConstants.LOGIN}" type="text"
                           class="validate" value="${user.login}"
                        <c:if test="${pageTitle == AttributeValueConstants.ACCOUNT_PAGE}">disabled</c:if> required>
                    <label for="${FormParameterConstants.LOGIN}">Login</label>
                </div>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.PASSWORD}" id="${FormParameterConstants.PASSWORD}" type="password"
                           class="validate" required>
                    <label for="${FormParameterConstants.PASSWORD}">Password</label>
                </div>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.PASSWORD_CONFIRM}" id="${FormParameterConstants.PASSWORD_CONFIRM}"
                           type="password" class="validate" required>
                    <label for="${FormParameterConstants.PASSWORD_CONFIRM}">Confirm Password</label>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="submit">Submit
                    <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />