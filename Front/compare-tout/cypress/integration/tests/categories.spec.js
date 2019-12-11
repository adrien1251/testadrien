import { setRoutes } from "./routes.spec";

describe('Categories Scenarii', function () {
  beforeEach(function () {
    // Routes definitions
    cy.clearCookies();

    setRoutes();


  });

  it('default user goes to Mobile category', function () {
    cy.visit('/');
    cy.wait('@initCategories')
      .its('status')
      .should('eq', 200);
  });

});
