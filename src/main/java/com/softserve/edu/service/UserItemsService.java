package com.softserve.edu.service;

import com.softserve.edu.dao.UserItemDao;
import com.softserve.edu.dto.UserItemDto;
import com.softserve.edu.entity.UserItemEntity;
import com.softserve.edu.exception.AuthorizationException;
import com.softserve.edu.exception.IncorrectParametersException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserItemsService {

    private static final Logger logger = Logger.getLogger(UserItemsService.class);

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

    public UserItemDto getUserItemFromParameter(HttpServletRequest request)
            throws IncorrectParametersException, AuthorizationException{
        return new UserItemDto(userItemDao.findById(secureGetUserItemId(request.getParameter("id"), request)));
    }

    public UserItemDto getUserItemFromPath(HttpServletRequest request)
            throws IncorrectParametersException, AuthorizationException{
        String itemId = StringUtils.replace(request.getPathInfo(), "/", "");
        return new UserItemDto(userItemDao.findById(secureGetUserItemId(itemId, request)));
    }

    public void createUserItem(HttpServletRequest request) throws IncorrectParametersException {
        saveUserEntity(null, request);
    }

    public void editUserItem(HttpServletRequest request) throws IncorrectParametersException, AuthorizationException {
        Long userItemId = secureGetUserItemId(request.getParameter("itemId"), request);
        saveUserEntity(userItemId, request);
    }

    public void deleteUserItem(HttpServletRequest request) throws IncorrectParametersException, AuthorizationException {
        userItemDao.deleteById(secureGetUserItemId(request.getParameter("itemId"), request));
    }

    private Long secureGetUserItemId(String itemIdString, HttpServletRequest request)
            throws IncorrectParametersException, AuthorizationException{
        Long itemId;
        try {
            itemId = Long.parseLong(itemIdString);
        } catch (NumberFormatException e) {
            logger.error(e);
            throw new IncorrectParametersException();
        }
        if (!LoginService.getLoggedUserId(request).equals(userItemDao.findById(itemId).getUserId())) {
            throw new AuthorizationException();
        }
        return itemId;
    }

    private void saveUserEntity(Long userItemId, HttpServletRequest request)
            throws IncorrectParametersException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(title)) {
            throw new IncorrectParametersException();
        }
        Long userId = LoginService.getLoggedUserId(request);
        userItemDao.save(new UserItemEntity(userItemId, title, description, userId));
    }

}
