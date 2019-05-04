import Route from '@ember/routing/route';
import { get } from '@ember/object';
import { inject as service } from '@ember/service';

export default Route.extend({
  auth: service(),

  beforeModel() {
    if (get(this, 'auth.authenticated')) {
      this.transitionTo('users.index')
    }
  }
});
