package com.centrumhr.application.account;

import com.centrumhr.application.account.data.AccountData;

/**
 * Created by Seweryn on 18.09.2016.
 */
public interface ILoginService {

    void login(AccountData account);

    void logout();

}
