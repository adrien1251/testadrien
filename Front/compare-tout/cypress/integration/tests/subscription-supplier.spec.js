import {
  setRoutes
} from "./routes.spec";

describe('Supplier Scenarii', function () {
  beforeEach(function () {
    // Routes definitions
    cy.clearCookies();

    setRoutes();


  });

  it('default supplier subscribes', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.account')
      .click();
    cy.get('.mat-tab-header-pagination-after')
      .click();
    cy.get('.mat-tab-label')
      .eq(2)
      .click();
    cy.get('#mat-input-2')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('#mat-input-3')
      .click()
      .type(Cypress.config('defaultEmailSupplier'));
    cy.get('#mat-input-4')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('#mat-input-5')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('#mat-input-6')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('#mat-input-7')
      .click()
      .type(Cypress.config('defaultPhone'));
    cy.get('.mat-button')
      .click();
    cy.wait('@subscriptionSupplier')
      .its('status')
      .should('eq', 200);
  });

  it('default supplier is not validated yet and logs in', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.account')
      .click();
    cy.get('.mat-tab-label')
      .eq(0)
      .click();
    cy.get('#mat-input-0')
      .click()
      .type(Cypress.config('defaultEmailSupplier'));
    cy.get('#mat-input-1')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('.mat-button')
      .click();
    cy.wait('@loginCustomer')
      .its('status')
      .should('eq', 500);
  });

  it('default admin logs in and validates the supplier', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.account')
      .click();
    cy.get('.mat-tab-label')
      .eq(0)
      .click();
    cy.get('#mat-input-0')
      .click()
      .type(Cypress.config('defaultAdmin'));
    cy.get('#mat-input-1')
      .click()
      .type(Cypress.config('defaultAdminPwd'));
    cy.get('.mat-button')
      .click();
    cy.wait('@loginCustomer')
      .its('status')
      .should('eq', 200);
    cy.get('.menu')
      .click();
    cy.get('[routerlink="/admin/supplier"] > .mat-list-item-content')
      .click();
    cy.wait('@suppliersToValidate')
      .its('status')
      .should('eq', 201);
    cy.get('#mat-checkbox-2')
      .first()
      .click();
    cy.get('.btn-validate')
      .click();
    cy.wait('@validateSupplier')
      .its('status')
      .should('eq', 201);
  });

  it('default supplier is validated and logs in', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.account')
      .click();
    cy.get('.mat-tab-label')
      .eq(0)
      .click();
    cy.get('#mat-input-0')
      .click()
      .type(Cypress.config('defaultEmailSupplier'));
    cy.get('#mat-input-1')
      .click()
      .type(Cypress.config('defaultSupplier'));
    cy.get('.mat-button')
      .click();
    cy.wait('@loginCustomer')
      .its('status')
      .should('eq', 200);
  });

  it('default supplier logs out', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.menu')
      .click();
    cy.get('.mat-list-item-content')
      .eq(1)
      .click();
  });
});
