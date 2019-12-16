export const setRoutes = function () {
  return cy.server()
    .route('GET', 'categories/_search').as('initCategories')
    .route('GET', 'categories/_search?category_id=*').as('childCategories')
    .route('POST', 'products/_search/*').as('retrieveProducts')
    .route('GET', 'criteria?id_product=*').as('retrieveCriteria')
    .route('POST', 'customers').as('subscriptionCustomer')
    .route('POST', 'auth/_login').as('loginCustomer')
    .route('POST', 'suppliers').as('subscriptionSupplier')
    .route('GET', 'suppliers?filter=notValidate').as('suppliersToValidate')
    .route('PATCH', 'suppliers/*').as('validateSupplier')

};
