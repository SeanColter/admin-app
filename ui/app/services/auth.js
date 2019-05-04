import Service, { inject as service } from '@ember/service';
import { get, set } from '@ember/object';

import authenticateQuery from "admin-app/gql/queries/authenticate";
import deauthenticateQuery from "admin-app/gql/queries/deauthenticate";

export default Service.extend({
  apollo: service(),
  router: service(),

  authenticated: false,

  async login(email, password) {
    console.log('auth service login', { email, password });
    if (email && password) {

      // let result = await get(this, 'apollo').query({
      //   query: authenticateQuery,
      //   variables: {
      //     email,
      //     password
      //   }
      // });
      // console.log('eh result', result);

      set(this, 'authenticated', true);
    }
    else {
      return Promise.reject();
    }
  },

  logout() {
    console.log('auth service logout');
    // get(this, 'apollo').query({ query: deauthenticateQuery });
    set(this, 'authenti1cated', false);
    get(this, 'router').transitionTo('login');
  }
});
