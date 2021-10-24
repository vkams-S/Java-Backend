
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

private StockQuotesService stockQuotesService;
private RestTemplate restTemplate;
PortfolioManagerImpl(StockQuotesService stockQuotesService){
  this.stockQuotesService = stockQuotesService;
}
public List<Candle> getStockQuote(String symbol,LocalDate startDate,LocalDate endLocalDate) throws JsonProcessingException, StockQuoteServiceException
{
return stockQuotesService.getStockQuote(symbol, startDate, endLocalDate);
}


  

  // Caution: Do not delete or modify the constructor, or else your build will
  // break!
  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }








  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.

/* 
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
        if(from.compareTo(to)>=0)
        {
          throw new RuntimeException();
        }
        String url= buildUri(symbol,from,to);
        TiingoCandle[] stockStartToEnd = restTemplate.getForObject(url,TiingoCandle[].class);
        if(stockStartToEnd == null)
        {
          return new ArrayList<Candle>();
        }
        else
        {
          List<Candle> stocksList = Arrays.asList(stockStartToEnd);
          return stocksList;
        }
       
     
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    final String TOKEN = "5809b334ea87db2ca6f91fdd932d96af17c6eb75";
    
       String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
        String url=uriTemplate.replace("$SYMBOL",symbol).replace("$STARTDATE",startDate.toString())
        .replace("$ENDDATE",endDate.toString()).replace("$APIKEY",TOKEN);

            return url;
  } */

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades, LocalDate endDate) throws StockQuoteServiceException {
    // TODO Auto-generated method stub
    AnnualizedReturn annualizedReturn;
    List<AnnualizedReturn> annualizedReturns = new ArrayList<>();
    for (int i=0; i< portfolioTrades.size();i++)
    {
      annualizedReturn = getAnnualizedReturn(portfolioTrades.get(i),endDate);
      annualizedReturns.add(annualizedReturn);
    }
    Collections.sort(annualizedReturns,getComparator());
    return annualizedReturns;

  }


  public  AnnualizedReturn getAnnualizedReturn(PortfolioTrade trade, LocalDate endLocalDate) throws StockQuoteServiceException {
    String ticker = trade.getSymbol();
    LocalDate startLocalDate = trade.getPurchaseDate();
    AnnualizedReturn annualizedReturn;
    try{
        //Fetch data
        List<Candle> stockStartToEnd ;
        stockStartToEnd = getStockQuote(ticker,startLocalDate,endLocalDate);
        Candle stockStartDate = stockStartToEnd.get(0);
        Candle stockLatest = stockStartToEnd.get(stockStartToEnd.size()-1);
        Double buyPrice = stockStartDate.getOpen();
        Double sellPrice = stockLatest.getClose();
        //calculating total return
        double totalReturn =(sellPrice - buyPrice)/buyPrice;
        double total_num_years = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endLocalDate) / 365.24;
        double annualizedReturns = Math.pow((1 + totalReturn),(1 / total_num_years))-1;
        annualizedReturn = new  AnnualizedReturn(trade.getSymbol(), annualizedReturns, totalReturn);
    }
    catch(JsonProcessingException e)
    {
      annualizedReturn = new AnnualizedReturn(ticker, Double.NaN, Double.NaN);  
    }
    return annualizedReturn;
    /* Double buyPrice=0.0,sellPrice=0.0;
    try{
      LocalDate startDate = trade.getPurchaseDate();
      List<Candle> stocksStartToEndFull = getStockQuote(ticker, startDate, endLocalDate);
      Collections.sort(stocksStartToEndFull,(candle1,candle2) ->{
        return candle1.getDate().compareTo(candle2.getDate());
      });
      Candle stockStartDate = stocksStartToEndFull.get(0);
      Candle stockLatest = stocksStartToEndFull.get(stocksStartToEndFull.size()-1);
      buyPrice = stockStartDate.getOpen();
      sellPrice = stockStartDate.getClose();
      endLocalDate = stockLatest.getDate();
    } catch(JsonProcessingException e)
    {
      throw new RuntimeException();
    }
    Double totalReturn = (sellPrice - buyPrice)/buyPrice;
    Double total_num_years = ChronoUnit.DAYS.between(startLocalDate, endLocalDate) / 365.24;
    Double annualizedReturns = Math.pow((1 + totalReturn),(1 / total_num_years))-1;
      return new  AnnualizedReturn(trade.getSymbol(), annualizedReturns, totalReturn); */

    }
    
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.
  @Override
  public
  List<AnnualizedReturn> calculateAnnualizedReturnParallel(List<PortfolioTrade> portfolioTrades,LocalDate endDate, int numThreads) throws InterruptedException,
    StockQuoteServiceException,RuntimeException{
      List<AnnualizedReturn> annualizedReturns = new ArrayList<>();
      /* for(int i=0;i<portfolioTrades.size();i++)
      {
        PortfolioTrade trade = portfolioTrades.get(i);
        AnnualizedReturn annreturn = getAnnualizedReturn(trade, endDate);
        annualizedReturns.add(annreturn);
      }
      Collections.sort(annualizedReturns,Collections.reverseOrder());
      return annualizedReturns; */
      List<Future<AnnualizedReturn>> futureReturnsList = new ArrayList<Future<AnnualizedReturn>>();
  final ExecutorService pool = Executors.newFixedThreadPool(numThreads);
  for (int i = 0; i < portfolioTrades.size(); i++) {
    PortfolioTrade trade = portfolioTrades.get(i);
    Callable<AnnualizedReturn> callableTask = () -> {
      return getAnnualizedReturn(trade, endDate);
    };
    Future<AnnualizedReturn> futureReturns = pool.submit(callableTask);
    futureReturnsList.add(futureReturns);
  }

  for (int i = 0; i < portfolioTrades.size(); i++) {
    Future<AnnualizedReturn> futureReturns = futureReturnsList.get(i);
    try {
      AnnualizedReturn returns = futureReturns.get();
      annualizedReturns.add(returns);
    } catch (ExecutionException e) {
      throw new StockQuoteServiceException("Error when calling the API", e);

    }
  }
  Collections.sort(annualizedReturns,getComparator());
  return annualizedReturns;

    }

}








  


