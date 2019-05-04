import Route from '@ember/routing/route';
import { get } from '@ember/object';
import { inject as service } from '@ember/service';

import { RouteQueryManager } from "ember-apollo-client";

import query from "admin-app/gql/queries/users";

export default Route.extend(RouteQueryManager, {
  auth: service(),

  beforeModel() {
    if (!get(this, 'auth.authenticated')) {
      this.transitionTo('login');
    }
  },

  model() {
    // return get(this, 'apollo').watchQuery({ query });

    return [
      {
        id: '1',
        username: 'alice',
        email: 'alice@alice.ca',
        role: 'ADMIN',
        active: true
      },
      {
        id: '2',
        username: 'bob',
        email: 'bob@bob.ca',
        role: 'REGULAR',
        active: true
      },
      {
        id: '3',
        username: 'charles',
        email: 'charles@charles.ca',
        role: 'VIEWER',
        active: true
      },
      {
        id: '4',
        username: 'damien',
        email: 'damien@damien.ca',
        role: 'REGULAR',
        active: false
      }
    ];
  }
});
