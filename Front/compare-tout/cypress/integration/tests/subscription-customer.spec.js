import {
  setRoutes
} from "./routes.spec";

describe('Customer Scenarii', function () {
  beforeEach(function () {
    // Routes definitions
    cy.clearCookies();

    setRoutes();


  });

  it('default user subscribes', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.account')
      .click();
    cy.get('.mat-tab-label')
      .eq(1)
      .click();
    cy.get('#mat-input-2')
      .click()
      .type(Cypress.config('defaultMsg'));
      cy.get('#mat-input-3')
      .click()
      .type(Cypress.config('defaultMsg'));
      cy.get('#mat-input-4')
      .click()
      .type(Cypress.config('defaultEmail'));
      cy.get('#mat-input-5')
      .click()
      .type(Cypress.config('defaultMsg'));
      cy.get('#mat-input-6')
      .click()
      .type(Cypress.config('defaultMsg'));
      cy.get('#mat-input-7')
      .click()
      .type(Cypress.config('defaultPhone'));
      cy.get('.mat-select-placeholder')
      .click();
      cy.get('#mat-option-0')
      .click();
      cy.get('#mat-input-8')
      .click()
      .type(Cypress.config('defaultBday'));
      cy.get('.mat-button')
      .click();
      cy.wait('@subscriptionCustomer')
      .its('status')
      .should('eq', 200);
  });

  it('default user logs in', function () {
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
      .type(Cypress.config('defaultEmail'));
      cy.get('#mat-input-1')
      .click()
      .type(Cypress.config('defaultMsg'));
      cy.get('.mat-button')
      .click();
      cy.wait('@loginCustomer')
      .its('status')
      .should('eq', 200);
  });

  it('default user logs out', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.menu')
      .click();
    cy.get('.mat-list-item-content')
      .click();
  });

});
