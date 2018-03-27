package com.softserve.edu.service;

import com.softserve.edu.constants.FormParameters;
import com.softserve.edu.constants.UrlParameters;
import com.softserve.edu.dao.UserItemDao;
import com.softserve.edu.dto.UserItemDto;
import com.softserve.edu.entity.UserItemEntity;
import com.softserve.edu.exception.AccessViolationException;
import com.softserve.edu.exception.EmptyFieldsException;
import com.softserve.edu.exception.IncorrectParametersException;
import com.softserve.edu.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserItemsService {

    public static final String INCORRECT_ID_FORMAT_MESSAGE = "Incorrect ID format";
    public static final String ITEM_NOT_FOUND_MESSAGE = "Could not find requested item";
    public static final String ACCESS_VIOLATION_MESSAGE = "Access Denied. You don't have permission to view this page";
    public static final String EMPTY_ITEM_FIELDS_MESSAGE = "Incorrect item parameters. Fields can't be empty";

    private UserItemDao userItemDao;

    public UserItemsService(UserItemDao userItemDao) {
        this.userItemDao = userItemDao;
    }

    public List<UserItemDto> getUserItems(HttpServletRequest request) {
        Long userId = LoginService.getLoggedUserId(request);
        ArrayList<UserItemDto> userItemDtos = new ArrayList<>();
        userItemDao.findByUserId(userId).forEach(userItemEntity -> userItemDtos.add(new UserItemDto(userItemEntity)));
        return userItemDtos;
    }

    public UserItemDto getUserItemFromUrlParameter(HttpServletRequest request)
            throws IncorrectParametersException, ResourceNotFoundException, AccessViolationException {
        Long id = verifyAndGetUserItemId(request.getParameter(UrlParameters.ID), LoginService.getLoggedUserId(request));
        return new UserItemDto(userItemDao.findById(id));
    }

    public UserItemDto getUserItemFromUrl(HttpServletRequest request)
            throws IncorrectParametersException, ResourceNotFoundException, AccessViolationException {
        String itemIdString = StringUtils.replace(request.getPathInfo(), "/", "");
        Long itemId = verifyAndGetUserItemId(itemIdString, LoginService.getLoggedUserId(request));
        return new UserItemDto(userItemDao.findById(itemId));
    }

    public void createUserItem(HttpServletRequest request) throws EmptyFieldsException {
        saveUserItem(null, request);
    }

    public void editUserItem(HttpServletRequest request)
            throws IncorrectParametersException, ResourceNotFoundException, AccessViolationException, EmptyFieldsException {
        Long id = verifyAndGetUserItemId(request.getParameter(FormParameters.ID), LoginService.getLoggedUserId(request));
        saveUserItem(id, request);
    }

    public void deleteUserItem(HttpServletRequest request)
            throws IncorrectParametersException, ResourceNotFoundException, AccessViolationException {
        Long id = verifyAndGetUserItemId(request.getParameter(UrlParameters.ID), LoginService.getLoggedUserId(request));
        userItemDao.deleteById(id);
    }

    private Long verifyAndGetUserItemId(String itemIdString, Long loggedUserId)
            throws IncorrectParametersException, ResourceNotFoundException, AccessViolationException {
        Long itemId;
        try {
            itemId = Long.parseLong(itemIdString);
        } catch (NumberFormatException e) {
            throw new IncorrectParametersException(INCORRECT_ID_FORMAT_MESSAGE);
        }
        UserItemEntity userItemEntity = userItemDao.findById(itemId);
        if (userItemEntity == null) {
            throw new ResourceNotFoundException(ITEM_NOT_FOUND_MESSAGE);
        }
        if (!userItemEntity.getUserId().equals(loggedUserId)) {
            throw new AccessViolationException(ACCESS_VIOLATION_MESSAGE);
        }
        return itemId;
    }

    private void saveUserItem(Long userItemId, HttpServletRequest request)
            throws EmptyFieldsException {
        String title = request.getParameter(FormParameters.TITLE);
        String description = request.getParameter(FormParameters.DESCRIPTION);
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(title)) {
            throw new EmptyFieldsException(EMPTY_ITEM_FIELDS_MESSAGE);
        }
        Long userId = LoginService.getLoggedUserId(request);
        userItemDao.save(new UserItemEntity(userItemId, title, description, userId));
    }

}
