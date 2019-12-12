import { setRoutes } from "./routes.spec";
import { assertPlatform } from "@angular/core";

describe('Categories Scenarii', function () {
  beforeEach(function () {
    // Routes definitions
    cy.clearCookies();

    setRoutes();


  });

  it('default user goes to Mobile category', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get(':nth-child(3) > .category-title')
      .click();
    cy.wait('@childCategories')
      .its('status')
      .should('eq', 200);
    cy.get('#more')
      .click();
    cy.get('.sub-category')
      .contains('Téléphonie')
      .click();
    cy.wait('@childCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.subcategory-list')
      .contains('Téléphones mobiles')
      .click();
    cy.wait('@retrieveProducts')
      .its('status')
      .should('eq', 201);
    cy.wait('@retrieveCriteria')
      .its('status')
      .should('eq', 200);
    cy.get('.result-title')
      .contains(Cypress.config('defaultNbProducts'));
  });

  it('default user filters by price and brand in Mobile category', function () {
    cy.visit('/category/all');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
    cy.get(':nth-child(3) > .category-title')
      .click();
    cy.wait('@childCategories')
      .its('status')
      .should('eq', 200);
    cy.get('#more')
      .click();
    cy.get('.sub-category')
      .contains('Téléphonie')
      .click();
    cy.wait('@childCategories')
      .its('status')
      .should('eq', 200);
    cy.get('.subcategory-list')
      .contains('Téléphones mobiles')
      .click();
    cy.wait('@retrieveProducts')
      .its('status')
      .should('eq', 201);
    cy.wait('@retrieveCriteria')
      .its('status')
      .should('eq', 200);
    cy.get('.result-title')
      .contains(Cypress.config('defaultNbProducts'));

  });

});
