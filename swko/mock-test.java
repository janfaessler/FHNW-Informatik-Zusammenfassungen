	private IMovie movie;
	private LowStockListener listener;
	private Stock stock;
    
    @Before
    public void setUp() {
    	movie = createStrictMock(IMovie.class);
    	listener = createStrictMock(LowStockListener.class);
        stock = new Stock();
    }
    
    @After
    public void tearDown() {
    	movie = null;
    	listener = null;
        stock = null;
    } 
    @Test
    public void testAddToStock() {
                
        expect(movie.getTitle()).andReturn("title").times(2);

        replay(movie);
        
        stock.addToStock(movie);
        assertEquals(1, stock.getInStock("title"));
        
        verify(movie);
    }
    @Test
    public void testRemoveFromStock() {
        
        expect(movie.getTitle()).andReturn("title").times(4);
        
        replay(movie);
        
        stock.addToStock(movie);
        stock.removeFromStock(movie);
        assertEquals(0, stock.getInStock("title"));
        
        verify(movie);
    }
    @Test
    public void testListener(){
    	
    	//adding movie four times
    	expect(movie.getTitle()).andReturn("Wurst").times(8);
    	
    	//remove movie
    	expect(movie.getTitle()).andReturn("Wurst").times(2);
    	expect(listener.getThreshold()).andReturn(2);
    	
    	//remove movie
    	expect(movie.getTitle()).andReturn("Wurst").times(2);
    	expect(listener.getThreshold()).andReturn(2);
    	listener.stockLow(movie,2);
    	
    	//remove movie
    	expect(movie.getTitle()).andReturn("Wurst").times(2);
    	expect(listener.getThreshold()).andReturn(2);
    	listener.stockLow(movie,1);
    	
    	//remove movie
    	expect(movie.getTitle()).andReturn("Wurst").times(2);
    	expect(listener.getThreshold()).andReturn(2);
    	listener.stockLow(movie,0);
    	
    	replay(movie);
    	replay(listener);
    	
    	stock.addLowStockListener(listener);
    	stock.addToStock(movie);
    	stock.addToStock(movie);
    	stock.addToStock(movie);
    	stock.addToStock(movie);    	
    	stock.removeFromStock(movie);
    	stock.removeFromStock(movie);
    	stock.removeFromStock(movie);
    	stock.removeFromStock(movie);
    	stock.removeLowStockListener(listener);
    	
    	verify(movie);
    	verify(listener);
    }
