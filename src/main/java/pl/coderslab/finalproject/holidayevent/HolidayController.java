package pl.coderslab.finalproject.holidayevent;

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
@RequestMapping("/holiday")
public class HolidayController {
    private final HolidayEventRepository holidayEventRepository;



        @GetMapping("/list")
        public String list(@RequestParam(defaultValue = "0") int page, Model model){
            Page<HolidayEvent> holidayEvents = holidayEventRepository.findAll(PageRequest.of(page,20));
            //model.addAttribute("stores",storeRepository.findAll());
            model.addAttribute("holidays",holidayEvents);
            return "/holiday/list";
        }

        @GetMapping("/new")
        public String add (Model model){
            model.addAttribute("holiday", new HolidayEvent());
            model.addAttribute("isEdit", false);
            return "/holiday/add";
        }

        @PostMapping("/new")
        public String create(@Valid HolidayEvent holidayEvent, BindingResult result,
                             @RequestParam(defaultValue = "false") boolean isEdit,
                             RedirectAttributes ra, Model model){

            if (result.hasErrors()){
                model.addAttribute("isEdit", isEdit);
                return "/holiday/add";
            }
            holidayEventRepository.save(holidayEvent);
            return "redirect:/holiday/list";
        }

        @GetMapping("/{holidayId}/delete")
        public String add(@PathVariable Long holidayId){
            holidayEventRepository.deleteById(holidayId);
            return "redirect:/holiday/list";
        }

        @GetMapping("/{holidayID}/edit")
        public String edit(@PathVariable Long holidayID, Model model){
            model.addAttribute("holiday", holidayEventRepository.findById(holidayID).orElseThrow());
            model.addAttribute("isEdit", true);
            return "/holiday/add";
        }



}
