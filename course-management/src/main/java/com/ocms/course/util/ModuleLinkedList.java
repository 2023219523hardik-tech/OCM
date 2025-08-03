package com.ocms.course.util;

import com.ocms.course.entity.Module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModuleLinkedList {
    private final LinkedList<Module> modules;

    public ModuleLinkedList() {
        this.modules = new LinkedList<>();
    }

    public ModuleLinkedList(List<Module> moduleList) {
        this.modules = new LinkedList<>(moduleList);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void addModuleAtPosition(int position, Module module) {
        if (position >= 0 && position <= modules.size()) {
            modules.add(position, module);
        } else {
            modules.add(module);
        }
    }

    public Module removeModule(int position) {
        if (position >= 0 && position < modules.size()) {
            return modules.remove(position);
        }
        return null;
    }

    public Module getModule(int position) {
        if (position >= 0 && position < modules.size()) {
            return modules.get(position);
        }
        return null;
    }

    public List<Module> getAllModules() {
        return new ArrayList<>(modules);
    }

    public int size() {
        return modules.size();
    }

    public boolean isEmpty() {
        return modules.isEmpty();
    }

    public void clear() {
        modules.clear();
    }

    public Module getFirst() {
        return modules.isEmpty() ? null : modules.getFirst();
    }

    public Module getLast() {
        return modules.isEmpty() ? null : modules.getLast();
    }

    public void updateOrderIndices() {
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).setOrderIndex(i + 1);
        }
    }
}