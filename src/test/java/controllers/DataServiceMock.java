package controllers;

import services.DatabaseSeederService;

public class DataServiceMock extends DatabaseSeederService {

    private boolean executed = false;

    @Override
    public void deleteAllExceptAdmin() {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}
