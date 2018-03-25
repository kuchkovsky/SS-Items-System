<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constant.PagePaths" %>
<%@ page import="com.softserve.edu.constant.AttributeValues" %>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="form-block center-block">
            <form action="${pageTitle == AttributeValues.ACCOUNT_PAGE ? PagePaths.USER_ACCOUNT : PagePaths.SIGNUP}"
                  class="col" method="post">
                <c:if test="${not empty error}">
                    <div id="error-block" class="center">
                        <span>${error.message}</span>
                    </div>
                </c:if>
                <div class="input-field col s12">
                    <input name="first-name" id="first-name" type="text" class="validate" value="${user.firstName}" required>
                    <label for="first-name">First Name</label>
                </div>
                <div class="input-field col s12">
                    <input name="last-name" id="last-name" type="text" class="validate" value="${user.lastName}" required>
                    <label for="last-name">Last Name</label>
                </div>
                <div class="input-field col s12">
                    <input name="login" id="login" type="text" class="validate" value="${user.login}"
                        <c:if test="${pageTitle == AttributeValues.ACCOUNT_PAGE}">disabled</c:if> required>
                    <label for="login">Login</label>
                </div>
                <div class="input-field col s12">
                    <input name="password" id="password" type="password" class="validate" required>
                    <label for="password">Password</label>
                </div>
                <div class="input-field col s12">
                    <input name="password-confirm" id="password-confirm" type="password" class="validate" required>
                    <label for="password-confirm">Confirm Password</label>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="submit">Submit
                    <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />