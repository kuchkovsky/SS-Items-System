package com.softserve.edu;

import com.softserve.edu.dao.ItemDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dao.impl.ItemDaoImpl;
import com.softserve.edu.dao.impl.UserDaoImpl;
import com.softserve.edu.entity.ItemEntity;
import com.softserve.edu.entity.UserEntity;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        ItemDao itemDao = new ItemDaoImpl();
        logger.info("Filling tables...");
        for (int i = 0; i < 10; i++) {
            userDao.save(new UserEntity("rms", "freedom", "Richard", "Stallman"));
            Long userId = userDao.findByLogin("rms").getId();
            itemDao.save(new ItemEntity("Free Software", "Free as freedom, not as free beer", userId));
        }
        logger.info("Finished. Printing...");
        userDao.findAll().forEach(userEntity -> {
            System.out.println(userEntity);
            userDao.delete(userEntity);
        });
    }

}
