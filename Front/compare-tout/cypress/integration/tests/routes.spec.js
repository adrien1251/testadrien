export const setRoutes = function () {
  return cy.server()
    .route('GET', 'categories/_search').as('initCategories')
    .route('GET', 'categories/_search?category_id=*').as('childCategories')
    .route('POST', 'products/_search/*').as('retrieveProducts')
    .route('GET', 'criteria?id_product=*').as('retrieveCriteria')
    
};
