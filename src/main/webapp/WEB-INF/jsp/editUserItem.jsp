<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constant.PagePaths" %>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="form-block center-block">
            <form action="${empty userItem ? PagePaths.ADD_USER_ITEM : PagePaths.EDIT_USER_ITEM}" class="col" method="post">
                <c:if test="${not empty userItem}">
                    <input type="hidden" name="itemId" value="${userItem.id}" />
                </c:if>
                <div class="input-field col s12">
                    <input name="title" id="title" type="text" class="validate" value="${userItem.title}" required>
                    <label for="title">Title</label>
                </div>
                <div class="input-field col s12">
                    <textarea value="test" name="description" id="description" class="materialize-textarea" required>${userItem.description}</textarea>
                    <label for="description">Description</label>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="submit">Submit
                    <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />