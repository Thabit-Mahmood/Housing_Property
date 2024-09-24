package controller;

import model.Admin;
import model.Property;
import services.PropertyApprovalService;

public class AdminController {
    private PropertyApprovalService approvalService;

    public AdminController(Admin admin) {
        this.approvalService = PropertyApprovalService.getInstance();
    }

    // Approves a property submitted by a seller
    public void approveProperty(Property property) {
        approvalService.approveProperty(property);
    }

    // Rejects a property submitted by a seller
    public void rejectProperty(Property property) {
        approvalService.rejectProperty(property);
    }

    // Get a pending property for review
    public Property getPendingProperty() {
        return approvalService.getPendingProperty();
    }
}
