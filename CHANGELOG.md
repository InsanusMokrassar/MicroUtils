# Changelog

## 0.1.0

Inited :)

### 0.1.1

* `Versions`:
    * `kotlinx.serialization`: `1.0.0-RC2` -> `1.0.0`
* `Repos`
    * `Common`
        * Extensions `doForAll` and `getAll` were added for all current types of repos:
            * `ReadStandardCRUDRepo`
            * `StandardReadKeyValueRepo`
            * `OneToManyReadKeyValueRepo`
        * `StandardReadKeyValueRepo` methods `values` and `keys` now have default value for `reversed` parameter `false`
