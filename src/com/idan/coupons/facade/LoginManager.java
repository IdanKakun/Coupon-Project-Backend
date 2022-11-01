package com.idan.coupons.facade;


import com.idan.coupons.enums.ClientType;
import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.exceptions.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

//A class that defines the login to the system.
public class LoginManager {
    public static final LoginManager instance = new LoginManager();
    public Set<String> authenticatedUsers = new HashSet<>();

    private final AdminFacade adminFacade = AdminFacade.instance;
    private final CompanyFacade companyFacade = CompanyFacade.instance;
    private final CustomerFacade customerFacade = CustomerFacade.instance;

    //Checks the login details, adds the details to the authenticated users,
    // returns the appropriate business logic to the client who logged in.
    public ClientFacade login(final String email, final String password, final ClientType clientType) throws Exception {

        boolean isAuthenticated;
        ClientFacade clientFacade;


        switch (clientType) {
            case ADMIN:
                clientFacade = adminFacade;
                break;
            case COMPANY:
                clientFacade = companyFacade;
                break;
            case CUSTOMER:
                clientFacade = customerFacade;
                break;

            default:
                throw new ApplicationException(ErrorType.ILLEGAL_USER_INPUT, "Field to authenticate " + email);
        }

        isAuthenticated = clientFacade.login(email, password);
        if (isAuthenticated) {
            authenticatedUsers.add(email);
            return clientFacade;
        }
        throw new ApplicationException(ErrorType.ILLEGAL_USER_INPUT, "Field to authenticate " + email);
    }
}


