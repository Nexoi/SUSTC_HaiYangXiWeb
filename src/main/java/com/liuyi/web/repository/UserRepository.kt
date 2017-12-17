package com.liuyi.web.repository

import com.liuyi.web.Model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 05/12/2017
 * Time: 6:29 PM
 * Describe:
 */

interface UserRepository : JpaRepository<User, String> {

}