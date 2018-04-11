<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constants.PagePathConstants" %>
<%@ page import="com.softserve.edu.constants.FormParameterConstants" %>
<c:set var="pageTitle" value="Login" scope="request"/>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="form-block center-block">
            <form action="${PagePathConstants.LOGIN}" class="col" method="post">
                <c:if test="${not empty error}">
                    <div id="error-block" class="center">
                        <span>${error.message}</span>
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.LOGIN}" id="${FormParameterConstants.LOGIN}" type="text" class="validate" required>
                    <label for="${FormParameterConstants.LOGIN}">Login</label>
                </div>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.PASSWORD}" id="${FormParameterConstants.PASSWORD}" type="password"
                           class="validate" required>
                    <label for="${FormParameterConstants.PASSWORD}">Password</label>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="submit">Submit
                    <i class="material-icons right">send</i>
                </button>
            </form>
            <br>
            <br>
            <h5 class="center">Don't Have an Account?</h5>
            <br>
            <a href="${PagePathConstants.SIGNUP}" class="btn waves-effect waves-light">Sign Up</a>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />