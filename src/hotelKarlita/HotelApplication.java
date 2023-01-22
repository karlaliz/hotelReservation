package hotelKarlita;

import hotelKarlita.services.ReservationService;

public class HotelApplication {

    public static final int MILLS_IN_A_DAY = 24 * 60 * 60 * 1000;
    public static final String EMAIL_REGEX = "^\\s*(\\S+@\\S+\\.com)\\s*$";

    public static void main(String[] args) {

        ReservationService.testDates();
        ReservationService.loadInitialData();
        MainMenu.getInstance().mainMenu();
    }


}



