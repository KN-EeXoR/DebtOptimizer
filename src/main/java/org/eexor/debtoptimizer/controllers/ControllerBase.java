package org.eexor.debtoptimizer.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ControllerBase<T> {
    List<T> getAll();
    T getById(@PathVariable("id") long id);
    void add(@RequestBody T t);
    void addMany(@RequestBody List<T> tList);
    void remove(@PathVariable("id") long id);
    void update(@PathVariable("id") long id,
                @RequestBody T t);
}
