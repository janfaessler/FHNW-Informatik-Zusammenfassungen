    @Test
    public void testUser() {
        User u = new User(NAME, FIRSTNAME, null);
        assertNotNull("u should not be null", u);

        // check if name and firstname were stored correctly
        String n = u.getName();
        String f = u.getFirstName();
        assertEquals(NAME, n);
        assertEquals(FIRSTNAME, f);

        // check if there exists a rental list
        List<Rental> rentals = u.getRentals();
        assertNotNull("rentals list should be empty, not null", rentals);
        assertEquals(0, rentals.size());
    }
    @Test
    public void testUserExceptions() {
        try {
            new User(null, FIRSTNAME, null);
        } catch (NullPointerException e) {
            assertEquals("invalid name value", e.getMessage());
        }
        try {
            new User(NAME, null, null);
        } catch (NullPointerException e) {
            assertEquals("invalid firstName value", e.getMessage());
        }
        try {
            new User(EMPTYSTRING, FIRSTNAME, null);
        } catch (MovieRentalException e) {
            assertEquals("invalid name value", e.getMessage());
        }
        try {
            new User(NAME, EMPTYSTRING, null);
        } catch (MovieRentalException e) {
            assertEquals("invalid firstName value", e.getMessage());
        }
        try { // a birth date in the future should raise an exception
            Calendar futureDate = Calendar.getInstance();
            futureDate.add(Calendar.YEAR, 1);
            new User(NAME, FIRSTNAME, futureDate);
        } catch (IllegalArgumentException e) {
            assertEquals("Birth date in future.", e.getMessage());
        }
        assertNotNull(new User(NAME, FIRSTNAME, null));
    }
    @Test(expected = MovieRentalException.class)
    public void testSetterGetterId() {
        User u = new User(NAME, FIRSTNAME, null);
        u.setId(42);
        assertEquals(42, u.getId());
        u.setId(0);
    }
