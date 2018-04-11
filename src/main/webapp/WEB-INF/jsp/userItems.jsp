<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.softserve.edu.constants.PagePathConstants" %>
<%@ page import="com.softserve.edu.constants.UrlParameterConstants" %>
<c:set var="pageTitle" value="User Items" scope="request"/>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <c:if test="${not empty userItems}">
            <div class="container item-list">
                <div class="collection">
                    <c:forEach items="${userItems}" var="item">
                        <div class="collection-item avatar">
                            <a href="${PagePaths.USER_ITEMS += '/' += item.id}">
                                <img src="/img/ic_star_black_48px.svg" alt="" class="circle">
                                <span class="title">
                                        ${fn:length(item.title) < 25 ?
                                                item.title : fn:substring(item.title, 0, 25) += '...'}
                                </span>
                                <p>
                                        ${fn:length(item.description) < 25 ?
                                                item.description : fn:substring(item.description, 0, 25) += '...'}
                                </p>
                            </a>
                            <div class="list-buttons">
                                <a href="${PagePaths.EDIT_USER_ITEM}?${UrlParameters.ID}=${item.id}"
                                   class="btn waves-effect waves-light edit-button">Edit</a>
                                <button class="btn waves-effect waves-light delete-button modal-trigger"
                                        data-target="confirmation-dialog" data-id="${item.id}">Delete</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${empty userItems}">
            <h5 class="center-align">
                Your item list is currently empty. Press the floating button to add your first item :)
            </h5>
        </c:if>
        <div id="confirmation-dialog" class="modal">
            <div class="modal-content center">
                <h4>Are you sure want to delete this item?</h4>
            </div>
            <div class="modal-footer">
                <button data-target="confirmation-dialog"
                        class="modal-action modal-close waves-effect waves-green btn-flat modal-delete">Yes</button>
                <button data-target="confirmation-dialog"
                        class="modal-action modal-close waves-effect waves-green btn-flat">No</button>
            </div>
        </div>
        <div id="error-dialog" class="modal">
            <div class="modal-content center">
                <h4>Failed to delete item</h4>
            </div>
            <div class="modal-footer">
                <button data-target="error-dialog"
                        class="modal-action modal-close waves-effect waves-green btn-flat">Close</button>
            </div>
        </div>
    </div>
    <div class="fixed-action-btn">
        <a class="btn-floating btn-large red" href="${PagePaths.ADD_USER_ITEM}">
            <i class="large material-icons">mode_edit</i>
        </a>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />