<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constants.PagePathConstants" %>
<%@ page import="com.softserve.edu.constants.FormParameterConstants" %>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="form-block center-block">
            <form action="${empty userItem ? PagePathConstants.ADD_USER_ITEM : PagePathConstants.EDIT_USER_ITEM}" class="col" method="post">
                <c:if test="${not empty userItem}">
                    <input type="hidden" name="${FormParameterConstants.ID}" value="${userItem.id}" />
                </c:if>
                <div class="input-field col s12">
                    <input name="${FormParameterConstants.TITLE}" id="${FormParameterConstants.TITLE}" type="text"
                           class="validate" value="${userItem.title}" required>
                    <label for="${FormParameterConstants.TITLE}">Title</label>
                </div>
                <div class="input-field col s12">
                    <textarea name="${FormParameterConstants.DESCRIPTION}" id="${FormParameterConstants.DESCRIPTION}"
                              class="materialize-textarea" required>${userItem.description}</textarea>
                    <label for="${FormParameterConstants.DESCRIPTION}">Description</label>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="submit">Submit
                    <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />