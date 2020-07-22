package com.practice.tacos.controller;

import com.practice.tacos.model.Ingredient;
import com.practice.tacos.model.Ingredient.Type;
import com.practice.tacos.model.Order;
import com.practice.tacos.model.Taco;
import com.practice.tacos.model.repository.IngredientRepository;
import com.practice.tacos.model.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController
{
  private final IngredientRepository ingredientRepo;
  private final TacoRepository tacoRepository;

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }
  @ModelAttribute(name = "taco")
  public Taco taco()
  {
    return new Taco();
  }

  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository)
  {
    this.ingredientRepo = ingredientRepo;
    this.tacoRepository = tacoRepository;
  }

  /**
   * TO show design taco page
   * @param model
   * @return
   */
  @GetMapping
  public String showDesignForm(Model model)
  {
    List<Ingredient> ingredients = new ArrayList<>();

    ingredientRepo.findAll().forEach(n -> ingredients.add(n));

    Type[] types = Ingredient.Type.values();
    for (Type type : types)
    {
      model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    }

    model.addAttribute("design", new Taco());

    return "design";
  }

  /**
   * TO process a taco created by user. If it has errors or it should be added to database.
   * @param taco
   * @param errors
   * @param order
   * @return
   */
  @PostMapping
  public String processDesign(@Valid Taco taco, Errors errors, @SessionAttribute Order order, HttpSession session)
  {
    if (errors.hasErrors())
    {
      return "home";
    }
    //Save data here
    Taco saved = tacoRepository.save(taco);
    order.addTaco(taco);

    return "redirect:/orders/current";
  }

  /**
   * Filter each taco ingredient by its type to show it on UI.
   * @param ingredients
   * @param type
   * @return
   */
  private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type)
  {
    List<Ingredient> ingredientList = new ArrayList<>();
    for (Ingredient ingredient : ingredients)
    {
      if (ingredient.getType().equals(type))
      {
        ingredientList.add(ingredient);
      }
    }
    return ingredientList;
  }

  /**
   * Not being used as we are getting ingredients from database.
   * @return
   */
  private List<Ingredient> getIngredients()
  {
    /*
    return Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE));*/
    return  null;
  }
}
