package pl.coderslab.finalproject.stores;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreRepository storeRepository;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Store> stores = storeRepository.findAll(PageRequest.of(page,20));
        //model.addAttribute("stores",storeRepository.findAll());
        model.addAttribute("stores",stores);
        return "/store/list";
    }

    @GetMapping("/new")
    public String add (Model model){
        model.addAttribute("store", new Store());
        model.addAttribute("isEdit", false);
        return "/store/add";
    }

    @PostMapping("/new")
    public String create(@Valid Store store, BindingResult result,
                          @RequestParam(defaultValue = "false") boolean isEdit,
                          RedirectAttributes ra, Model model){

        if (!isEdit && storeRepository.existsById(store.getStoreNumber())) {
            result.rejectValue(
                    "storeNumber",
                    "duplicate.storeNumber",
                    "Store with this number already exists"
            );
        }
        if (result.hasErrors()){
            model.addAttribute("isEdit", isEdit);
            return "/store/add";
        }
        storeRepository.save(store);
        return "redirect:/stores/list";
    }

    @GetMapping("/{storeNumber}/delete")
    public String add(@PathVariable Integer storeNumber){
        storeRepository.deleteById(storeNumber);
        return "redirect:/stores/list";
    }

    @GetMapping("/{storeNumber}/edit")
    public String edit(@PathVariable Integer storeNumber, Model model){
        model.addAttribute("store", storeRepository.findById(storeNumber).orElseThrow());
        model.addAttribute("isEdit", true);
        return "/store/add";
    }


}
