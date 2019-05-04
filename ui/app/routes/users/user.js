import Route from '@ember/routing/route';
import { get } from '@ember/object';
import { inject as service } from '@ember/service';

import { RouteQueryManager } from "ember-apollo-client";

import query from "admin-app/gql/queries/user";

export default Route.extend(RouteQueryManager, {
  auth: service(),

  beforeModel() {
    if (!get(this, 'auth.authenticated')) {
      this.transitionTo('login')
    }
  },

  model({ id }) {
    if (id === 'new') {
      return { id };
    }
    else {
      return get(this, 'apollo').watchQuery({
        query,
        variables: { id }
      });

      // return {
      //   id: '1',
      //   username: 'alice',
      //   email: 'alice@alice.ca',
      //   role: 'ADMIN',
      //   active: true
      // };
    }
  }
});
