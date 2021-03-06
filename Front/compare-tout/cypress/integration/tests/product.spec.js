import {
  setRoutes
} from "./routes.spec";

describe('Product Scenarii', function () {
      beforeEach(function () {
        // Routes definitions
        cy.clearCookies();

        setRoutes();


      });

      it('default user compares 2 products', function () {
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
        cy.get('.mat-checkbox-inner-container')
          .eq(1)
          .click();
        cy.get('.mat-checkbox-inner-container')
          .eq(2)
          .click();
        cy.get('.button-confirm')
        .click();
      });


    });
    
