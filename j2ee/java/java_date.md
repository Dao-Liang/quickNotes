
### Using a String format Date to create a Date object ###

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            reportDate = f.parse("2014-10-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reportDate
