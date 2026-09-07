package pl.coderslab.finalproject.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.holidayevent.HolidayEventRepository;
import pl.coderslab.finalproject.items.Item;
import pl.coderslab.finalproject.items.ItemRepository;
import pl.coderslab.finalproject.sale.SaleRepository;
import pl.coderslab.finalproject.stores.StoreRepository;
import pl.coderslab.finalproject.stores.StoreTester;
import pl.coderslab.finalproject.storetransaction.StoreTransaction;
import pl.coderslab.finalproject.storetransaction.StoreTransactionRepository;

@RequiredArgsConstructor
@Service
public class AnalyticsService {
    private final ItemRepository itemRepository;
    private final SaleRepository saleRepository;
    private final StoreRepository storeRepository;
    private final StoreTransactionRepository storeTransactionRepository;



    public DashboeardStatisticsDto basicStats(){
        DashboeardStatisticsDto dashboeardStatisticsDto = new DashboeardStatisticsDto();
        dashboeardStatisticsDto.setStoreCount(storeRepository.count());
        dashboeardStatisticsDto.setProductsCount(itemRepository.count());
        dashboeardStatisticsDto.setSaleRecordsCount(saleRepository.count());
        dashboeardStatisticsDto.setCombinedSales(storeTransactionRepository.countAlltransactions());


        return dashboeardStatisticsDto;
    }
}
