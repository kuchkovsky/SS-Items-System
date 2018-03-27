<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constants.PagePaths" %>
<c:set var="pageTitle" value="Error" scope="request"/>
<c:import url="/WEB-INF/page-parts/top.jsp" charEncoding="utf-8"/>
<main>
    <div class="section">
        <div class="center">
            <h5>${error.message}</h5>
            <br>
            <a href="${PagePaths.ROOT}" class="btn waves-effect waves-light center">Go to Main Page</a>
        </div>
    </div>
</main>
<c:import url="/WEB-INF/page-parts/bottom.html" charEncoding="utf-8" />