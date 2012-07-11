import org.apache.log4j.Logger;

    /** declare the movie-logger. **/
    private static Logger logger = Logger.getLogger(Movie.class);

    public Movie(String aTitle, PriceCategory aPriceCategory, int ageRating) {
        logger.trace("entering constructor Movie (without aReleaseDate)");
        if (logger.isDebugEnabled()) {
            logger.debug("aTitle: " + aTitle + " aPriceCategory: " + aPriceCategory + " ageRating: " + ageRating);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("releaseDate: " + this.releaseDate + " rented: " + this.rented);
            logger.debug("Movie '" + aTitle + "' created"); 
        }
        logger.trace("exiting constructor Movie (without aReleaseDate)");
    }

    public PriceCategory getPriceCategory() {
        logger.debug("getPriceCategory: " + priceCategory);
        logger.trace("exiting Movie.getPriceCategory");
        return priceCategory;
    }

    if (this.title != null) {
        IllegalStateException e = new IllegalStateException();
        logger.error("Exception:", e);
        throw e;
     }