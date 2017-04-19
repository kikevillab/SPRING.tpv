package controllers;

import services.DatabaseSeederService;

public class DatabaseSeederServiceMock extends DatabaseSeederService {

    private boolean executed = false;

    @Override
    public void deleteAllExceptAdmin() {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

}
