export const setRoutes = function () {
  return cy.server()
    .route('GET', 'categories/_search').as('initCategories')
    
};
